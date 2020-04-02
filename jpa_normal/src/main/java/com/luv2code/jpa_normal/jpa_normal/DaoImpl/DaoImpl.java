package com.luv2code.jpa_normal.jpa_normal.DaoImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.luv2code.jpa_normal.jpa_normal.Bean.Employee;
import com.luv2code.jpa_normal.jpa_normal.Dao.Dao;

@RestController
@Transactional
public class DaoImpl implements Dao {

	@Autowired
	EntityManager entityManager;

	@Autowired
	Dao dao;

	@Override
	@RequestMapping(path = "/findAll", method = RequestMethod.GET)
	public List<Employee> findAll() {
		Query findQuery = entityManager.createQuery("from Employee");
		List<Employee> retList = findQuery.getResultList();
		return retList;
	}

	@RequestMapping(path = "/findById/{id}", method = RequestMethod.GET)
	public List<Employee> findById(@PathVariable int id) {
		Query findQuery = entityManager.createQuery("from Employee where id=:employeeId");
		findQuery.setParameter("employeeId", id);
		List<Employee> retList = findQuery.getResultList();
		return retList;
	}

	@RequestMapping(path = "/findByName/{firstname}", method = RequestMethod.GET)
	public List<Employee> findByName(@PathVariable String firstname) {
		Query findQuery = entityManager.createQuery("from Employee where firstname=:employeeName");
		findQuery.setParameter("employeeName", firstname);
		List<Employee> retList = findQuery.getResultList();
		return retList;
	}
	
	@RequestMapping(path = "/findByNameAndName/{firstname}/and/{lastname}", method = RequestMethod.GET)
	public List<Employee> findByNameAndName(@PathVariable String firstname, @PathVariable String lastname){
		Query findQuery = entityManager.createQuery("from Employee where firstname=:employeeName1 and lastname=:employeeName2");
		findQuery.setParameter("employeeName1", firstname);
		findQuery.setParameter("employeeName2", lastname);
		List<Employee> retList = findQuery.getResultList();
		return retList;
	}
	
	@RequestMapping(path = "/addEmployee", method = RequestMethod.POST)
	public String addEmployee(@RequestBody Employee theEmployee) {
		if(findById(theEmployee.getId()).isEmpty()) {
			entityManager.merge(theEmployee);
		}
		else {
			return theEmployee.getFirstName()+" already Exists";
		}
		
		return theEmployee.getFirstName()+" successfully added";
	}

	@RequestMapping(path = "/deleteEmployee/{theId}", method = RequestMethod.DELETE)
	public String deleteEmployee(@PathVariable int theId) {
		if(findById(theId).isEmpty()) {
			return "The data is not Present";
		}
		else {
			Query query=entityManager.createQuery("delete from employee where id=:theId");
			query.setParameter("theId", theId);
			return theId+" is delted successfully";
		}
		//return theEmployee.getFirstName()+" successfully added";
	}
	
}
