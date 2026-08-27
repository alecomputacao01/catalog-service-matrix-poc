package br.com.algar.poc.catalog.arch;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

/**
 * Suíte de conformidade para o estilo Clean Architecture (plan.md §9.2). Anéis: entities (mais
 * interno) → usecases → adapters → frameworks (mais externo). Regra de dependência: sempre de fora
 * para dentro.
 *
 * Decisão de design (não estava 100% especificada na diretiva original): a interface
 * ProductRepository ("boundary"/gateway) mora em usecases, não em entities — é a inversão de
 * dependência clássica de Clean Architecture (o círculo interno nunca conhece o externo, mas define
 * o contrato que o externo deve implementar). ProductJpaEntity/ProductJpaRepository/
 * ProductRepositoryImpl ficam em "adapters.out.persistence" (não em "frameworks"), porque colocá-los
 * em frameworks criaria uma dependência circular (adapters precisaria depender de frameworks para
 * persistir, violando a regra "adapters não depende de frameworks"). "frameworks" fica reservado ao
 * composition root (BeanConfig, FlywayConfig, classe principal) — o único lugar com liberdade total
 * para depender de qualquer coisa do projeto.
 */
@AnalyzeClasses(packages = "br.com.algar.poc.catalog", importOptions = ImportOption.DoNotIncludeTests.class)
class CleanArchitectureTest {

    private static final String BASE_PACKAGE = "br.com.algar.poc.catalog";

    @ArchTest
    static final ArchRule entities_nao_depende_de_nada_do_projeto_fora_de_si_mesma =
            noClasses().that().resideInAPackage(BASE_PACKAGE + ".entities..")
                    .should().dependOnClassesThat().resideInAnyPackage(
                            BASE_PACKAGE + ".usecases..",
                            BASE_PACKAGE + ".adapters..",
                            BASE_PACKAGE + ".frameworks..");

    @ArchTest
    static final ArchRule entities_nao_depende_de_framework =
            noClasses().that().resideInAPackage(BASE_PACKAGE + ".entities..")
                    .should().dependOnClassesThat().resideInAnyPackage("org.springframework..", "jakarta.persistence..");

    @ArchTest
    static final ArchRule usecases_so_depende_de_entities =
            noClasses().that().resideInAPackage(BASE_PACKAGE + ".usecases..")
                    .should().dependOnClassesThat().resideInAnyPackage(
                            BASE_PACKAGE + ".adapters..",
                            BASE_PACKAGE + ".frameworks..");

    @ArchTest
    static final ArchRule usecases_nao_depende_de_framework =
            noClasses().that().resideInAPackage(BASE_PACKAGE + ".usecases..")
                    .should().dependOnClassesThat().resideInAnyPackage("org.springframework..", "jakarta.persistence..");

    @ArchTest
    static final ArchRule adapters_nao_depende_de_frameworks =
            noClasses().that().resideInAPackage(BASE_PACKAGE + ".adapters..")
                    .should().dependOnClassesThat().resideInAPackage(BASE_PACKAGE + ".frameworks..")
                    .allowEmptyShould(true);

    @ArchTest
    static final ArchRule boundary_de_repositorio_e_interface =
            classes().that().resideInAPackage(BASE_PACKAGE + ".usecases..")
                    .and().haveSimpleName("ProductRepository")
                    .should().beInterfaces();

    @ArchTest
    static final ArchRule caso_de_uso_de_entrada_e_interface =
            classes().that().resideInAPackage(BASE_PACKAGE + ".usecases..")
                    .and().haveSimpleName("RegisterProductUseCase")
                    .should().beInterfaces();
}
