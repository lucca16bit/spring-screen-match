# Anotações Spring Boot

- **@Controller:** Usada para marcar uma classe como um controlador no padrão MVC (Model-View-Controller). Essa anotação é usada para receber requisições e manipular lógica de negócios.
- **@RestController:** Uma variação de @Controller, específica para APIs RESTful. Combina as anotações @Controller e @ResponseBody, indicando que cada método retorna um objeto serializado diretamente em JSON ou XML como resposta.
- **@RequestMapping:** Define mapeamentos entre URLs e métodos de controlador. Especifica as URLs para as quais um método do controlador deve responder e os métodos HTTP correspondentes (GET, POST, PUT, DELETE etc.).
- **@GetMapping, @PostMapping, @PutMapping, @DeleteMapping:** Atalhos para as operações HTTP GET, POST, PUT e DELETE, respectivamente, em métodos de controlador.
- **@RequestParam:** Usada para mapear os parâmetros de requisição HTTP para os parâmetros do método do controlador.
- **@PathVariable:** Usada para vincular variáveis de template de URL a parâmetros de métodos de controlador.
- **@RequestBody:** Utilizada para mapear o corpo da requisição HTTP para um objeto de entrada do método do controlador.
- **@ResponseBody:** Indica que o valor retornado pelo método do controlador deve ser usado diretamente como corpo da resposta HTTP.
- **@Valid e @Validated:** Utilizadas para ativar a validação de entrada no lado do servidor. Geralmente combinadas com anotações de validação, como @NotNull, @Size, @Min, @Max, entre outras.
- **@CrossOrigin:** Utilizada para configurar permissões de acesso a recursos de diferentes origens (CORS - Cross-Origin Resource Sharing).
