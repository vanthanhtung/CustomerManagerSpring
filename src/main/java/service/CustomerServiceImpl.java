package service;

import model.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerServiceImpl implements ICustomerService{
    static List<Customer> customerList = new ArrayList<>();
    static {
        Customer customer1 = new Customer(1,"Tung","tung@gmail.com","Ha Noi");
        Customer customer2 = new Customer(1,"Nghia","nghia@gmail.com","Ha Tay");
        Customer customer3 = new Customer(1,"Van","van@gmail.com","Ha Noi 2");
        Customer customer4 = new Customer(1,"Lam","lam@gmail.com","Ha Dong");
        Customer customer5 = new Customer(1,"Hung","hung@gmail.com","Ha Nam");

        customerList.add(customer1);
        customerList.add(customer2);
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
