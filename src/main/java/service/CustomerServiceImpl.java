package service;

import model.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerServiceImpl implements ICustomerService{
    static List<Customer> customerList = new ArrayList<>();
    static {
        Customer customer1 = new Customer(1,"Tung","vantung@gmail.com","Ha Noi","1.jpg");
        Customer customer3 = new Customer(2,"Van","anhvan@gmail.com","Ha Tay","2.jpg");
        Customer customer4 = new Customer(3,"Lam","duclam@gmail.com","Ha Dong","3.jpg");
        Customer customer5 = new Customer(4,"Hung","phihung@gmail.com","Ha Nam","4.jpg");

        customerList.add(customer1);
        customerList.add(customer3);
        customerList.add(customer4);
        customerList.add(customer5);
    }

    @Override
    public List<Customer> findAll() {
        return customerList;
    }

    @Override
    public void save(Customer customer) {
        customerList.add(customer);
    }

    @Override
    public Customer findByID(int id) {
        Customer customer = new Customer();
        for (Customer x: customerList){
            if (x.getId()== id){
                customer = x;
            }
        }
        return customer;
    }

    @Override
    public void update(int id, Customer customer) {
        for (Customer x: customerList){
            if (x.getId()==id){
                x.setId(id);
                x.setName(customer.getName());
                x.setEmail(customer.getEmail());
                x.setAddress(customer.getAddress());
            }
            return;
        }
    }

    @Override
    public void remove(int id) {
        for (Customer x: customerList){
            if (x.getId()==id){
                customerList.remove(x);
            }
            return;
        }
    }
}
