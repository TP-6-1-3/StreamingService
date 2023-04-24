package ru.vsu.csf.asashina.musicmanBack.template;

import com.samskivert.mustache.Mustache.Compiler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MustacheTemplateEngine implements TemplateEngine {

    private final Compiler engine;

    @Override
    public String compile(String template, Object model) {
        return engine.compile(template).execute(model);
    }

    @Override
    public String compile(Template template, Object model) {
        return compile(String.format("{{>%s}}", template.getName()), model);
    }
}
