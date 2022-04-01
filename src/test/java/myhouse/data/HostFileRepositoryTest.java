package myhouse.data;

import myhouse.models.Host;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class HostFileRepositoryTest {

    private final String seedFilePath = "./dont-wreck-my-house-data/Test/hosts-seed.csv";
    private final String testFilePath = "./dont-wreck-my-house-data/Test/hosts-test.csv";
    private HostFileRepository repo = new HostFileRepository(testFilePath);

    @BeforeEach
    void setUp() throws IOException {
        Files.copy(Paths.get(seedFilePath),
                Paths.get(testFilePath),
                StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    void shouldFindHosts() throws DataAccessException {
       List<Host> hosts = repo.findAll();

       assertEquals(1000, hosts.size());
       assertEquals("Yearnes", hosts.get(0).getLastName());
    }

    @Test
    void shouldFindHostsByState() throws DataAccessException {
        List<Host> hosts = repo.findAllByState("TX");

        assertEquals(103, hosts.size());
        assertEquals("Yearnes", hosts.get(0).getLastName());

        List<Host> hostsMN = repo.findAllByState("mn");

        assertEquals(14, hostsMN.size());
        assertEquals("MN", hostsMN.get(0).getState());

    }

    @Test
    void shouldFindHostById() throws DataAccessException {
        Host host = repo.findHostById(UUID.fromString("3edda6bc-ab95-49a8-8962-d50b53f84b15"));

        assertEquals("Yearnes", host.getLastName());
    }

}