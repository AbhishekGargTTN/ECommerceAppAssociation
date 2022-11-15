package com.TTN.BootCamp.ECommerce_App;

import com.TTN.BootCamp.ECommerce_App.Entity.*;
import com.TTN.BootCamp.ECommerce_App.Repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootTest
class ECommerceAppApplicationTests {

	@Autowired
	UserRepo userRepository;

	@Autowired
	CustomerRepo customerRepo;

	@Autowired
	SellerRepo sellerRepo;

	@Autowired
	AddressRepo addressRepo;

	@Autowired
	RoleRepo roleRepo;

	@Test
	void contextLoads() {
	}

	@Test
	public void addCustomer(){
		User user=new User();
		Role role= new Role();
		role= roleRepo.findByRole("CUSTOMER");
		Customer customer= new Customer();
		user.setEmail("abhishek@gmail.com");
		user.setFirstName("Abhishek");
		user.setMiddleName("Ab");
		user.setLastName("Garg");
		user.setPassword("1234");
		user.addRole(role);
		customer.setContact(1234567890);
		customer.setUser(user);
		Set<Address> addresses= new HashSet<>();
		Address address1= new Address();
		address1.setCity("Shastri Nagar");
		address1.setState("Delhi");
		address1.setZipCode(110052);
		address1.setAddressLine("749");
		address1.setLabel("Home");
		address1.setCountry("India");
		address1.setCustomer(customer);
		addresses.add(address1);

		Address address2= new Address();
		address2.setCity("Rohini");
		address2.setState("Delhi");
		address2.setZipCode(110039);
		address2.setAddressLine("1234");
		address2.setLabel("Office");
		address2.setCountry("India");
		address2.setCustomer(customer);
		addresses.add(address2);

		customer.setAddresses(addresses);

		customerRepo.save(customer);
	}

	@Test
	public void addSeller(){

		User user= new User();
		Seller seller= new Seller();
		Role role= new Role();
		role= roleRepo.findByRole("SELLER");
		user.setEmail("tarun@gmail.com");
		user.setFirstName("Tarun");
		user.setMiddleName("K");
		user.setLastName("Singh");
		user.setPassword("1234");
		user.addRole(role);
		seller.setGst("abcgst123");
		seller.setCompanyName("TTN");
		seller.setCompanyContact(12345678);
		seller.setUser(user);

		Address address1= new Address();
		address1.setCity("Noida");
		address1.setState("UP");
		address1.setZipCode(121032);
		address1.setAddressLine("7987");
		address1.setLabel("Office");
		address1.setCountry("India");
		address1.setSeller(seller);

		seller.setAddress(address1);
		sellerRepo.save(seller);
	}

	@Test
	public void addRole(){
		Role role1= new Role();
		role1.setRole("ADMIN");
		roleRepo.save(role1);

		Role role2= new Role();
		role2.setRole("CUSTOMER");
		roleRepo.save(role2);

		Role role3= new Role();
		role3.setRole("SELLER");
		roleRepo.save(role3);
	}

	@Test
	public void retrieveCustomerAddressByFirstName(){
		User user=new User();
		user=userRepository.findByFirstName("Abhishek");
		long userId= user.getId();
		Customer customer=new Customer();
		customer=customerRepo.findByUser_Id(userId);
		long customerId= customer.getId();
		List<Address> addressList= new ArrayList<>();
		addressList=addressRepo.findByCustomer_ID(customerId);
		addressList.forEach(address -> System.out.println(address.toString()));
	}
}
