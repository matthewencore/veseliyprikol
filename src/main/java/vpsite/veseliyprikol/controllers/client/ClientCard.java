package vpsite.veseliyprikol.controllers.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import vpsite.veseliyprikol.services.Client;

@Slf4j
@Controller
@RequestMapping("/client/")
public class ClientCard {
    @Autowired
    Client client;

    @GetMapping("/{slug}")
    String clientCardLk(@PathVariable String slug, Model model){
            log.info("Запрос клиента по слагу [{}]...",slug);
            // Получаем объект клиента
            vpsite.veseliyprikol.models.client.Client client_ = client.get_client_by_slug(slug);
            model.addAttribute("client",client_);

            return "client/client-lk";
    }
}
