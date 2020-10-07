package controller;

import model.Customer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import service.CustomerServiceImpl;
import service.ICustomerService;

import java.util.List;

@Controller
public class CustomerController {
    private ICustomerService customerService = new CustomerServiceImpl();

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
    public String createNewCustomer(Customer customer, RedirectAttributes redirectAttributes){
        customer.setId((int) (Math.random()*10000));
        customerService.save(customer);
        redirectAttributes.addFlashAttribute("success", "Saved customer successfully!");
        return "redirect:/";
    }

    @GetMapping("/customer/{id}/edit")
    public String showEditForm(@PathVariable int id, Model model) {
        model.addAttribute("customer", customerService.findByID(id));
        return "/edit";
    }

    @PostMapping("/customer/update")
    public String update(Customer customer, RedirectAttributes redirect) {
        customerService.update(customer.getId(), customer);
        redirect.addFlashAttribute("success", "Modified customer successfully!");
        return "redirect:/";
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
