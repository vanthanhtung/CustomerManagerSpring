package controller;

import model.Customer;
import model.CustomerForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import service.CustomerServiceImpl;
import service.ICustomerService;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
public class CustomerController {

    @Autowired
    private Environment environment;

    @Autowired
    private ICustomerService customerService;

    @GetMapping("/")
    public String index(Model model){
        List<Customer> customerList = customerService.findAll();
        model.addAttribute("customerList",customerList);
        return "/index";
    }

    @GetMapping("/customer/create")
    public String showCreateForm(Model model){
        model.addAttribute("customer",new Customer());
        return "/create";
    }

    @PostMapping("/customer/save")
    public ModelAndView createNewCustomer(@ModelAttribute CustomerForm customerForm){
        ModelAndView modelAndView = new ModelAndView("/create");
        int id = (int) (Math.random()*1000);
        Customer customer = new Customer(id,customerForm.getName(),customerForm.getEmail(),customerForm.getAddress());

        MultipartFile multipartFile =customerForm.getImage();
        String image = multipartFile.getOriginalFilename();
        customer.setImage(image);
        String fileUpload = environment.getProperty("file_upload").toString();
        try {
            FileCopyUtils.copy(multipartFile.getBytes(),new File(fileUpload+image));
        } catch (IOException e) {
            e.printStackTrace();
        }
        customerService.save(customer);
        modelAndView.addObject("customer",new CustomerForm());
        return modelAndView;
    }

    @GetMapping("/customer/{id}/edit")
    public String showEditForm(@PathVariable int id, Model model) {
        model.addAttribute("customer", customerService.findByID(id));
        return "/edit";
    }

    @PostMapping("/customer/update")
    public ModelAndView update(@ModelAttribute CustomerForm customerForm) {
        ModelAndView modelAndView = new ModelAndView("/edit");
        Customer updateCustomer = customerService.findByID(customerForm.getId());
        updateCustomer.setEmail(customerForm.getEmail());
        updateCustomer.setAddress(customerForm.getAddress());
        updateCustomer.setName(customerForm.getName());

        MultipartFile file = customerForm.getImage();
        String image = file.getOriginalFilename();
        updateCustomer.setImage(image);
        String fileUpload = environment.getProperty("file_upload").toString();
        try {
            FileCopyUtils.copy(file.getBytes(),new File(fileUpload+image));
        } catch (IOException e) {
            e.printStackTrace();
        }
        customerService.update(customerForm.getId(),updateCustomer);
        modelAndView.addObject("customer",new CustomerForm());
        return modelAndView;
    }

    @GetMapping("/customer/{id}/delete")
    public String showDeleteForm(@PathVariable int id, Model model) {
        model.addAttribute("customer", customerService.findByID(id));
        return "/delete";
    }

    @PostMapping("/customer/delete")
    public String delete(Customer customer, RedirectAttributes redirect) {
        customerService.remove(customer.getId());
        redirect.addFlashAttribute("success", "Removed customer successfully!");
        return "redirect:/";
    }

    @GetMapping("/customer/{id}/view")
    public String viewDetail(@PathVariable int id, Model model) {
        model.addAttribute("customer", customerService.findByID(id));
        return "/view";
    }
}
