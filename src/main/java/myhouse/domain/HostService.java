package myhouse.domain;

import myhouse.data.DataAccessException;
import myhouse.data.HostRepository;
import myhouse.models.Host;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public class HostService {

    private final HostRepository repo;

    public HostService(HostRepository repo) {
        this.repo = repo;
    }

    public List<Host> findAllHosts() throws DataAccessException {
        return repo.findAll();
    }

    public List<Host> findHostsByState(String state) throws DataAccessException {
        return repo.findAllByState(state);
    }

    public Host findHostById(UUID hostId) throws DataAccessException {
        return repo.findHostById(hostId);
    }
}
