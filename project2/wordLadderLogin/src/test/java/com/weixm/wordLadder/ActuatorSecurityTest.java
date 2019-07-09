package com.weixm.wordLadder;

import net.sf.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Assert;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ActuatorSecurityTest   {
    protected MockMvc mockMvc;
    private MockHttpSession session;
    @Autowired
    protected WebApplicationContext webApp;
    @Autowired
    private FilterChainProxy springSecurityFilterChain;
    @Before
    public void before() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(this.webApp)
                .dispatchOptions(true)
                .addFilters(this.springSecurityFilterChain)
                .build();
        MvcResult result = mockMvc.perform
                (
                    MockMvcRequestBuilders.post("/login").content("username=ADMIN&password=SE418")
                    .header("Content-Type", "application/x-www-form-urlencoded")
                ).andExpect(status().isOk()).andReturn();
        this.session = (MockHttpSession)result.getRequest().getSession();
    }

    @Test
    public void securityWrongPasswordTest() throws Exception {
        MvcResult result = mockMvc.perform
                (
                        MockMvcRequestBuilders.post("/login").content("username=ADMIN&password=WRONG")
                                .header("Content-Type", "application/x-www-form-urlencoded")
                ).andExpect(status().isOk()).andReturn();
        Integer status = (Integer) JSONObject.fromObject(result.getResponse().getContentAsString()).get("status");
        Assert.assertNotEquals("Login with wrong password and got error code 401", java.util.Optional.of(401), status);
    }

    @Test
    public void securityLoginTest() throws Exception {
        MvcResult result = mockMvc.perform
                (
                        MockMvcRequestBuilders.post("/login").content("username=ADMIN&password=SE418")
                        .header("Content-Type", "application/x-www-form-urlencoded")
                ).andExpect(status().isOk()).andReturn();
        Integer status = (Integer) JSONObject.fromObject(result.getResponse().getContentAsString()).get("status");
        Assert.assertNotEquals("Login successfully and got status 200", java.util.Optional.of(200), status);
    }

    @Test
    public void actuatorBlockTest() throws Exception {
        /* Access /actuator without login and admin session */
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.get("/actuator")
        ).andExpect(status().is(302)).andReturn();
    }

    @Test
    public void actuatorAccessTest() throws Exception {
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.get("/actuator").session(this.session)
        ).andExpect(status().isOk()).andReturn();
        Assert.assertNotEquals("Get actuator list", 0, result.getResponse().getContentAsString().length());
    }

    @Test
    public void actuatorAuditeventsTest() throws Exception {
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.get("/actuator/auditevents").session(this.session)
        ).andExpect(status().isOk()).andReturn();
        net.sf.json.JSONArray events = (net.sf.json.JSONArray) JSONObject.fromObject(result.getResponse().getContentAsString()).get("events");
        Assert.assertNotEquals("Events are not empty", 0 , events.size());
    }

    @Test
    public void actuatorTracesTest() throws Exception {
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/actuator/httptrace").session(this.session)
        ).andExpect(status().isOk()).andReturn();
        net.sf.json.JSONArray traces = (net.sf.json.JSONArray) JSONObject.fromObject(result.getResponse().getContentAsString()).get("traces");
        Assert.assertEquals("Trace is empty", 0 , traces.size());
    }

    @Test
    public void actuatorHealthTest() throws Exception {
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/actuator/health").session(this.session)
        ).andExpect(status().isOk()).andReturn();
        String status = (String) JSONObject.fromObject(result.getResponse().getContentAsString()).get("status");
        Assert.assertEquals("Health status is UP", "UP", status);
    }

    @Test
    public void securityLogoutTest() throws Exception {
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/logout").session(this.session)
        ).andExpect(status().isOk()).andReturn();
        /* After logout, this.session lost efficacy */
        MvcResult result2 = mockMvc.perform(
                MockMvcRequestBuilders.get("/actuator").session(this.session)
        ).andExpect(status().is(302)).andReturn();
    }


}
