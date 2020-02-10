package com.example.blog.web.admin;

import com.example.blog.modle.Tag;
import com.example.blog.modle.Type;
import com.example.blog.service.TagService;
import com.example.blog.service.TypeService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;


@Controller
@RequestMapping("/admin")
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping("/tag")
    public String type(@PageableDefault(size = 10, sort = {"id"},direction = Sort.Direction.DESC)
                                   Pageable pageable, Model model){
        model.addAttribute("page",tagService.listTag(pageable));
        return "admin/tag";
    }

    @GetMapping("/tag/input")
    public String input(Model model){
        model.addAttribute("tag",new Tag());
        return "admin/tag-import";
    }
    @GetMapping("/tag/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("tag",tagService.getTag(id));
        return "admin/tag-import";
    }

    @PostMapping("/tag")
    public String post(@Valid Tag tag, BindingResult result, RedirectAttributes attributes){
        if (result.hasErrors()){
            return "admin/tag-import";
        }
        Tag tag1 = tagService.getTagByName(tag.getName());
        if (tag1 !=null){
            result.rejectValue("name","nameError","该标签已存在");
        }
        Tag t = tagService.saveTag(tag);
        if (t==null){
            attributes.addFlashAttribute("message", "新增失败");
        }else {
            attributes.addFlashAttribute("message", "新增成功");
        }
        return "redirect:/admin/tag";
    }

    @PostMapping("/tag/{id}")
    public String editPost(@Valid Tag tag, BindingResult result,@PathVariable Long id, RedirectAttributes attributes) throws NotFoundException {
        if (result.hasErrors()){
            return "admin/tag-import";
        }
        Tag tag1 = tagService.getTagByName(tag.getName());
        if (tag1 !=null){
            result.rejectValue("name","nameError","该标签已存在");
        }
        Tag t = tagService.updateTag(id,tag);
        if (t==null){
            attributes.addFlashAttribute("message", "更新失败");
        }else {
            attributes.addFlashAttribute("message", "更新成功");
        }
        return "redirect:/admin/tag";
    }
    @GetMapping("/tag/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes){
        tagService.deleteTag(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/tag";
    }
}
