package br.com.filmoteca.templates;

import static br.com.filmoteca.templates.FixtureCoreTemplates.VALID;
import static br.com.filmoteca.templates.FixtureCoreTemplates.VALID_THOR;

import br.com.filmoteca.controllers.resources.request.FilmeRequest;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import java.time.LocalDate;
import java.util.Arrays;

public class FilmeRequestTemplateLoader implements TemplateLoader {

  @Override
  public void load() {
    Fixture.of(FilmeRequest.class).addTemplate(VALID.name(), new Rule() {
      {
        add("nome",
            random("Thor: Ragnarok", "Capitão América: Guerra Civil", "Vingadores: Ultimato"));
        add("dataLancamento", LocalDate.now());
        add("nivelCensura", random("CENSURADO", "SEM_CENSURA"));
        add("direcao", random("Cate Shortland", "David Harbour", "Florence Pugh", "Rachel Weisz"));
        add("elenco",
            Arrays.asList("Samuel L. Jackson", "Clark Gregg", "Cobie Smulders", "Gwyneth Paltrow"));
      }
    }).addTemplate(VALID_THOR.name()).inherits(VALID.name(), new Rule() {{
      add("nome", "Thor: Ragnarok");
    }});
  }
}