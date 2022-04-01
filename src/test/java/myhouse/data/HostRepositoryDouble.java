package myhouse.data;

import myhouse.models.Host;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class HostRepositoryDouble implements HostRepository {

    private final ArrayList<Host> hosts = new ArrayList<>();

    public HostRepositoryDouble() {
        Host host = new Host(UUID.fromString("3edda6bc-ab95-49a8-8962-d50b53f84b15"),
                "Hosty",
                "McHosterson",
                "Hosty@Hosty.biz",
                "cal-lme-plez",
                "I live here",
                "Inthiscity",
                "IO",
                "five#",
                new BigDecimal("450"),
                new BigDecimal("600"));

        Host host2 = new Host(UUID.fromString("a0d911e7-4fde-4e4a-bdb7-f047f15615e8"),
                "Hostier",
                "McHostierson",
                "Hostier@Hostier.biz",
                "cal-lme-ples",
                "I live here 2",
                "Inthiscity",
                "IO",
                "five#",
                new BigDecimal("445"),
                new BigDecimal("595"));

        hosts.add(host);
        hosts.add(host2);
    }

    public List<Host> findAll() {
        return hosts;
    }

    public List<Host> findAllByState(String state) {
        return findAll().stream()
                .filter(a -> a.getState().equalsIgnoreCase(state))
                .collect(Collectors.toList());
    }

    public Host findHostById(UUID hostId) throws DataAccessException {
        Host result = new Host();

        for (Host h : findAll()) {
            if (h.getHostId().toString().equals(hostId.toString())) {
                result = h;
            }
        }

        return result;
    }

}
