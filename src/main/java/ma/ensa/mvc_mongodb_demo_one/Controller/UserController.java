package ma.ensa.mvc_mongodb_demo_one.Controller;

import lombok.AllArgsConstructor;
import ma.ensa.mvc_mongodb_demo_one.Beans.User;
import ma.ensa.mvc_mongodb_demo_one.Dao.IUserMongoDBRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.swing.text.Document;
import java.util.List;

@Controller
@AllArgsConstructor
public class UserController {
    private IUserMongoDBRepo userMongoDBRepo;

    @GetMapping(path = "/index")
    public String index(Model userModel,
                        @RequestParam(name="page",defaultValue="0")int page,
                        @RequestParam(name="size",defaultValue="10")int size){
        Page<User> usersPage = userMongoDBRepo.findAll(PageRequest.of(page,size));
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
    public String postDelete(@RequestParam(name="id") Long id){
        User user = userMongoDBRepo.findById(id).orElse(null);
        if(user == null){
            return "index";
        }
        userMongoDBRepo.delete(user);
        return "index";
    }
}
