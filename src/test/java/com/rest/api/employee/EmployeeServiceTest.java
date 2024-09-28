package com.rest.api.employee;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.rest.api.employee.model.Employee;
import com.rest.api.employee.repository.EmployeeRepository;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.security.test.context.support.WithMockUser;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class EmployeeServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeRepository mockRepository;

    @Before
    public void init() {
        Employee emp = new Employee();
        emp.setEmployeeId(1);
        emp.setFirstName("Navneet");
        emp.setLastName("Raushan");
        emp.setEmailId("nraushan119@gmail.com");
        emp.setPhoneNumbers(8309426798L);
        emp.setSalary(1700000);
        
        when(mockRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(emp));
    }

    @WithMockUser("USER")
    @Test
    public void find_login_ok() throws Exception {

        mockMvc.perform(get("/api/employees/1/tax-deductions"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is("Navneet")))
                .andExpect(jsonPath("$.lastName", is("Raushan")))
                .andExpect(jsonPath("$.yearlySalary", is(1700000.0)))
                .andExpect(jsonPath("$.taxAmount", is(5000.0)))
                .andExpect(jsonPath("$.cessAmount", is(2000.0)));
    }


    @Test
    public void find_nologin_401() throws Exception {
        mockMvc.perform(get("/api/employees/1/tax-deductions"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

}