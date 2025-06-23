package vpsite.veseliyprikol.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vpsite.veseliyprikol.repository.ClientProgramRepository;

@Service
@RequiredArgsConstructor
public class ClientProgram {
    private final ClientProgramRepository programRepository;
    private final ClientService clientService;


}
