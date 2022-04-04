package myhouse.data;

import myhouse.models.Host;

import java.util.List;
import java.util.UUID;

public interface HostRepository {

    // Interface for the Host Repository

    public List<Host> findAll() throws DataAccessException;

    public List<Host> findAllByState(String state) throws DataAccessException;

    public Host findHostById(UUID hostId) throws DataAccessException;
}
