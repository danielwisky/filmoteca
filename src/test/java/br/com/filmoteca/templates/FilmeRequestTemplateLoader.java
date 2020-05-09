package br.com.filmoteca.templates;

import static br.com.filmoteca.templates.FixtureCoreTemplates.INVALIDO_TAMANHO_ELENCO;
import static br.com.filmoteca.templates.FixtureCoreTemplates.VALIDO;
import static br.com.filmoteca.templates.FixtureCoreTemplates.VALIDO_THOR;

import br.com.filmoteca.controllers.resources.request.FilmeRequest;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import java.time.LocalDate;
import java.util.List;

public class FilmeRequestTemplateLoader implements TemplateLoader {

  @Override
  public void load() {
    Fixture.of(FilmeRequest.class).addTemplate(VALIDO.name(), new Rule() {
      {
        add("nome",
            random("Thor: Ragnarok", "Capitão América: Guerra Civil", "Vingadores: Ultimato"));
        add("dataLancamento", LocalDate.now());
        add("nivelCensura", random("CENSURADO", "SEM_CENSURA"));
        add("direcao", random("Cate Shortland", "David Harbour", "Florence Pugh", "Rachel Weisz"));
        add("elenco",
            List.of("Samuel L. Jackson", "Clark Gregg", "Cobie Smulders", "Gwyneth Paltrow"));
      }
    }).addTemplate(VALIDO_THOR.name()).inherits(VALIDO.name(), new Rule() {{
      add("nome", "Thor: Ragnarok");
    }}).addTemplate(INVALIDO_TAMANHO_ELENCO.name()).inherits(VALIDO.name(), new Rule() {{
      add("elenco",
          List.of("Samuel L. Jackson", "Clark Gregg", "Cobie Smulders", "Gwyneth Paltrow",
              "Robert Downey Jr.", "Chris Evans", "Mark Ruffalo", "Chris Hemsworth",
              "Scarlett Johansson", "Jeremy Renner", "Tom Hiddleston"));
    }});


  }
}