package br.edu.utfpr.td.tsi.delegacia.endpoint;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	
	@GetMapping("/")
	public String index() {
		return "index";
	}
	
    @GetMapping("/cadastroBoletim")
    public String cadastroBoletim() {
        return "cadastroBoletim";
    }

    @GetMapping("/listaBoletim")
    public String listaBoletim() {
        return "listaBoletim";
    }
}
