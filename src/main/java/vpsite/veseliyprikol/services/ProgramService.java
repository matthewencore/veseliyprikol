package vpsite.veseliyprikol.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vpsite.veseliyprikol.api.one_c_portal.json.ProgramAccessDto;
import vpsite.veseliyprikol.models.partner1c.Program;
import vpsite.veseliyprikol.repository.ProgramRepository;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class ProgramService {
    private final ProgramRepository programRepository;

    // Cоздание объекта программы
    public void createProgram(List<ProgramAccessDto> program){
        Program progr = new Program();

        // Цикл для переборки программ
        for (ProgramAccessDto pr: program){
            if (checkAvailable(pr.getProgram().getNick()).getNick() != null){
                log.info("Информация createProgram \n{} уже существует в базе.",pr.getProgram().getName());
                continue;
            }

            progr.setName(pr.getProgram().getName());
            progr.setBase(pr.getProgram().isBase());
            progr.setNick(pr.getProgram().getNick());
            progr.setDeveloperName(pr.getProgram().getDeveloperName());
            progr.setConfigurationName(pr.getProgram().getConfigurationName());

            log.info("+ Информация createProgram \n{} был создан", progr.getName());
            programRepository.save(progr);
        }

    }

    public Program checkAvailable(String nick){
        return programRepository
                .findByNick(nick)
                .orElseGet(Program::new);
    }

}
