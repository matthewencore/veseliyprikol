package vpsite.veseliyprikol.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vpsite.veseliyprikol.repository.ClientProgramRepository;

@Service
public class ClientProgram {
    @Autowired
    ClientProgramRepository programRepository;

    @Autowired
    Client clientService;


}
