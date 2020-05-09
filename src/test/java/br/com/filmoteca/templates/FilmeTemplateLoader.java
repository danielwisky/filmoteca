package br.com.filmoteca.templates;

import static br.com.filmoteca.templates.FixtureCoreTemplates.VALIDO;
import static br.com.filmoteca.templates.FixtureCoreTemplates.VALIDO_CENSURADO;
import static br.com.filmoteca.templates.FixtureCoreTemplates.VALIDO_SEM_CENSURA;
import static br.com.filmoteca.templates.FixtureCoreTemplates.VALIDO_THOR;

import br.com.filmoteca.domains.Filme;
import br.com.filmoteca.domains.NivelCensura;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import java.time.LocalDate;
import java.util.List;

public class FilmeTemplateLoader implements TemplateLoader {

  @Override
  public void load() {
    Fixture.of(Filme.class).addTemplate(VALIDO.name(), new Rule() {
      {
        add("nome",
            random("Thor: Ragnarok", "Capitão América: Guerra Civil", "Vingadores: Ultimato"));
        add("dataLancamento", LocalDate.now());
        add("nivelCensura", random(NivelCensura.class));
        add("direcao", random("Cate Shortland", "David Harbour", "Florence Pugh", "Rachel Weisz"));
        add("elenco",
            List.of("Samuel L. Jackson", "Clark Gregg", "Cobie Smulders", "Gwyneth Paltrow"));
      }
    }).addTemplate(VALIDO_THOR.name()).inherits(VALIDO.name(), new Rule() {{
      add("nome", "Thor: Ragnarok");
    }}).addTemplate(VALIDO_CENSURADO.name()).inherits(VALIDO.name(), new Rule() {{
      add("nome", "Filme: Censurado");
      add("nivelCensura", NivelCensura.CENSURADO);
    }}).addTemplate(VALIDO_SEM_CENSURA.name()).inherits(VALIDO.name(), new Rule() {{
      add("nome", "Filme: Sem Censura");
      add("nivelCensura", NivelCensura.SEM_CENSURA);
    }});
  }
}