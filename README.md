# t1-construcao-BookTracker

## Passos para criar outra instância do produto
- Criar uma cópia da pasta `software-product-line` usando esse comando no terminal;
``` sh
cp -r software-product-line/ minha-aplicacao/
```
- Criar uma classe que implementa a interface `UserFacade`, também deve anotar essa classe usando `@Component` no diretório `minha-aplicacao/src/main/java/br/pucrs/construcao/t1/backend/facade/`;
- Por fim, execute o back-end, utilizando a IDE de sua preferência.

```java
...
public interface UserFacade {
	boolean userExists(String userName);
	User create(User user);
	Optional<String> passwordOf(String userName);
	List<Book> booksOf(String userName);
	List<Book> saveBooks(List<Book> books, String userName);
}
...
```
Exemplo de implementação da `interface` `UserFacade`

```java 
...
@Component
public class UserFacadeImpl implements UserFacade {
	
	public UserFacadeImpl() {
		// ...
	}

	@Override
	public boolean userExists(String userName) {
		// ...
	}

	@Override
	public User create(User user) {
		// ...
	}
	
	@Override
	public Optional<String> passwordOf(String userName) {
		// ...
	}

	@Override
	public List<Book> booksOf(String userName) {
		// ...
	}

	@Override
	public List<Book> saveBooks(List<Book> books, String userName) {
		// ...
	}
}
...
```
## Instalação
Usando o terminal, vá para a pasta do projeto onde esta o arquivo `gradlew` e execute comando abaixo.
``` sh
gradlew clean build
java -jar build/libs/minha-aplicacao-0.0.1-SNAPSHOT.jar 
```

## Passos para executar a aplicação iOS

- Abra o diretório `ios/`
- Usando o terminal execute o comando abaixo(esse comando abre o Xcode).
```sh
xed .
```
- Após isso deve executar o projeto usando o comando `cmd` + `r` ou selecionando o botão de play.

## Requisitos
- Java 8 (OpenJDK)
- Xcode 10.2
