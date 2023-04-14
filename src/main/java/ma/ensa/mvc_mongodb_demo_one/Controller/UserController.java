package ma.ensa.mvc_mongodb_demo_one.Controller;

import lombok.AllArgsConstructor;
import ma.ensa.mvc_mongodb_demo_one.Beans.User;
import ma.ensa.mvc_mongodb_demo_one.Dao.IUserMongoDBRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Collection;
import java.util.List;

@Controller
@AllArgsConstructor
public class UserController {
    private IUserMongoDBRepo userMongoDBRepo;

    @GetMapping(path = "/index")
    public String index(Model userModel,
                        @RequestParam(name="page",defaultValue="0")int page,
                        @RequestParam(name="size",defaultValue="6")int size){
        Page<User> usersPage = userMongoDBRepo.findAll(PageRequest.of(page,size));
        userModel.addAttribute("users",usersPage.getContent());
        userModel.addAttribute("currentPage",page);
        userModel.addAttribute("size",size);
        userModel.addAttribute("pages",new int[usersPage.getTotalPages()]);
        return "index";
    }

    @PostMapping(path = "index")
    public String PostIndex(Model userModel,
                        @RequestParam(name="page",defaultValue="0")int page,
                        @RequestParam(name="size",defaultValue="6")int size,
                        @RequestParam(name="hint",defaultValue="")String hint){
        hint = hint.strip();
        Page<User> usersPage = userMongoDBRepo.findByFullnameContaining(hint,PageRequest.of(page,size));
        userModel.addAttribute("users",usersPage.getContent());
        userModel.addAttribute("currentPage",page);
        userModel.addAttribute("size",size);
        userModel.addAttribute("pages",new int[usersPage.getTotalPages()]);
        return "index";
    }

    @GetMapping(path = "/edit")
    public String getEdit(Model userModel,
            @RequestParam(name="id")Long id){
        User user = userMongoDBRepo.findById(id).orElse(null);
        if(user == null){
            return "index";
        }
        userModel.addAttribute("user",user);
        return "edit";
    }

    @PostMapping(path = "/edit")
    public String postEdit(@ModelAttribute("user") User user, Model userModel){
        userMongoDBRepo.save(user);
        userModel.addAttribute("user", user);
        return "updated";
    }

    @GetMapping(path = "/delete")
    public String getDelete(Model userModel,
                         @RequestParam(name="id")Long id){
        User user = userMongoDBRepo.findById(id).orElse(null);
        if(user == null){
            return "index";
        }
        userModel.addAttribute("user",user);
        return "delete";
    }

    @PostMapping(path = "/delete")
    public RedirectView postDelete(@RequestParam(name="id") Long id){
        User user = userMongoDBRepo.findById(id).orElse(null);
        final RedirectView redirectView = new RedirectView("index");
        if(user == null){
            return redirectView;
        }
        userMongoDBRepo.delete(user);
        return redirectView;
    }

    @GetMapping("/add")
    public String getAdd(Model userModel){
        List<User> users = userMongoDBRepo.findAll();
        Long id = users.get(users.size()-1).getId() + 1;
        userModel.addAttribute("id", id);
        return "add";
    }
}
