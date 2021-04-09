package com.cg.cms.ui;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import com.cg.cms.entity.Contact;
import com.cg.cms.exception.CMSException;
import com.cg.cms.service.ContactServiceImpl;
import com.cg.cms.service.IContactService;

public class CMSApplication {
	
	public static final IContactService contactService = new ContactServiceImpl();
	public static final Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) {
		Menu[] menus = Menu.values();
		Menu selectedMenu = null;
		
		while(selectedMenu != Menu.QUIT) {
			System.out.println("Choice\tOperation");
			for(Menu menu : menus)
			{
				System.out.println(menu.ordinal() + "\t" + menu);
			}
			System.out.print("Enter Choice : ");
			int ch = sc.nextInt();
			
			if (ch >=0 && ch <= menus.length) {
				selectedMenu = menus[ch];
				
				switch(selectedMenu) {
				case ADD : 
					doAdd();
					break;
				case FIND : 
					doFind();
					break;
				case LIST : 
					doList();
					break;
				case DELETE : 
					doDelete();
					break;
				case QUIT:
					break;
				}
			} else {
				selectedMenu = null;
				System.out.println("Invalid Choice");
			}
		}
			sc.close();
			System.out.println("Application Terminated");
	}
	
	private static void doAdd() {
		Contact contact  = new Contact();
		System.out.println("Enter contact Id : ");
		contact.setContactId(sc.nextLong());
		System.out.println("Enter Full Name : ");
		contact.setFullName(sc.next());
		System.out.println("Enter date of birth (yyyy-mm-dd) : ");
		contact.setDateOfBirth(LocalDate.parse(sc.next()));
		System.out.println("Enter mobile number : ");
		contact.setMobile(sc.next());
		
		try {
			contactService.add(contact);
			System.out.println("Contact Added");
		} catch(CMSException excep) {
			System.out.println(excep.getMessage());
		}
	}
	
	private static void doFind() {
		try {
		System.out.println("Enter contact Id : ");
		long contactId = sc.nextLong();
		
		Contact contact = contactService.getById(contactId);
		
		if(contact == null) {
			System.out.println("No Contact with : " +contactId);
		} else {
			System.out.print(contact);
		}
	} catch(CMSException excep)
		{
			System.out.println(excep.getMessage());
		}
	}
	
	public static void doList() {
		try {
			List<Contact> contacts = contactService.getAll();
			if(contacts.isEmpty()) {
				System.out.println("No Contacts Found");
			} else {
				for(Contact contact : contacts)
				{
					System.out.println(contact);
				}
			}
		} catch(CMSException excep)
		{
			System.out.println(excep.getMessage());
		}
	}
	
	public static void doDelete() {
		try {
			System.out.println("Enter contact Id : ");
			long contactId = sc.nextLong();
			boolean isDeleted = contactService.deleteById(contactId);
			if(!isDeleted)
			{
				System.out.println("No Such Contact Found");
			} else {
				System.out.println("Contact with #" +contactId + "is deleted");
			}
		} catch(CMSException excep)
		{
			System.out.println(excep.getMessage());
		}
	}

}
