# 99201 Daniel Maio

## Qodana-Dead Code

Escolhi o Dead Code porque, ao inspecionar o código do projeto, reparei que existiam reatribuições desnecessárias no construtor da classe "Ship" (linhas 116 a 118).

Ficheiros usados:
- `.github/workflows/qodana-smell-99201.yaml`
- `qodana-99201.yaml`
- `.qodana/profiles/profile-99201.xml`
 



- O perfil ficou em XML porque o linter Community teve problemas a interpretar a hierarquia do perfil em YAML.


### Tabela de mapeamento

| Cheiro | Inspeção no IDE | ID da inspeção | Métrica / limiar | Limitações |
| :--- | :--- | :--- | :--- | :--- |
| Dead Code | Unused assignment | `UnusedAssignment` | Sem métrica nem limiar | Foca-se em variáveis locais dentro do método .

