import { TemplateContainer, TemplateContent, TemplateDesignBottom, TemplateDesignTop } from "./styled";

interface ITemplate {
    children: React.ReactElement;
}

export const Template = ({ children }: ITemplate): React.ReactElement<ITemplate, string> => {
    return (
        <TemplateContainer>
            <TemplateDesignTop />
            <TemplateDesignBottom />
            <TemplateContent>
                {children}
            </TemplateContent>
        </TemplateContainer>
    )
}