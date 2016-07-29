package ua.dp.daragan.controller;

import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ua.dp.daragan.entity.Posts;
import ua.dp.daragan.entity.User;
import ua.dp.daragan.repository.UserRepository;

/**
 *
 * @author bogdan
 */
@Controller
public class newsController {
    
    @Autowired
    private UserRepository userRepo;
    
    @RequestMapping(value = "/news", method = RequestMethod.GET)
    public String search(Model m){
        
        List<Posts> posts = new LinkedList<Posts>();
        
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();  
        User me = userRepo.findByUsername(userName);
        
        for(User friend : me.getFriends() ){
            posts.addAll(friend.getPosts());
        }
        
        posts.sort((Posts o1, Posts o2) -> -1 * o1.getPostId().compareTo(o2.getPostId())); //compare by postId, desc
        
//        final PageRequest page1 = new PageRequest(
//  0, 20, Direction.ASC, "lastName", "salary"
//);
//
//final PageRequest page2 = new PageRequest(
//  0, 20, new Sort(
//    new Order(Direction.ASC, "lastName"), 
//    new Order(Direction.DESC, "salary")
//  )
//);
//
//dao.findAll(page1);

//PageRequest(int page, int size, Sort.Direction direction, String... properties) 
        
        m.addAttribute("allposts", posts );
        
        return "news";
    }
}