package myhouse.domain;

import myhouse.data.DataAccessException;
import myhouse.data.HostRepositoryDouble;
import myhouse.data.ReservationRepositoryDouble;
import myhouse.models.Host;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HostServiceTest {

    HostService service;

    @BeforeEach
    void init() {
        service = new HostService(new HostRepositoryDouble());
    }

    @Test
    void shouldFindAllHosts() throws DataAccessException {
       List<Host> hosts = service.findAllHosts();

        assertEquals(2, hosts.size());
    }

    @Test
    void shouldFindAllByState() throws DataAccessException {
        List<Host> hosts = service.findHostsByState("IO");

        assertEquals(2, hosts.size());
        assertEquals("McHosterson", hosts.get(0).getLastName());
    }

    @Test
    void shouldFindHostById() throws DataAccessException {
        Host host = service.findHostById(UUID.fromString("3edda6bc-ab95-49a8-8962-d50b53f84b15"));


        assertEquals("Hosty@Hosty.biz", host.getEmail());
        assertEquals("cal-lme-plez", host.getPhone());



    }


}
