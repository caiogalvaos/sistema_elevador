package com.teste;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.SistemaElevador.Main;
import com.SistemaElevador.business.ElevadorInterface;

@SpringJUnitConfig
@SpringBootTest(classes = Main.class)
public class ElevadorTest {

    @Autowired
    private ElevadorInterface elevador;
    private final int capacidadeMaxima = 5;
    private final int totalAndares = 10;

    @BeforeEach
    public void setUp() {
        elevador.inicializarElevador(capacidadeMaxima, totalAndares);
    }

    @Test
    void testAdicionarPessoa() {
        elevador.adicionarPessoa("Alice");
        assertTrue(elevador.getPessoas().contains("Alice"));
    }

    @Test
    void testRemoverPessoa() {
        elevador.adicionarPessoa("Alice");
        elevador.adicionarPessoa("Bob");
        assertTrue(elevador.removerPessoa("Alice"));
        assertFalse(elevador.removerPessoa("Charlie")); // Pessoa não está no elevador
    }

    @Test
    void testSubir() {
        assertTrue(elevador.subir());
        assertTrue(elevador.subir());
        assertEquals(2, elevador.getAndarAtual()); // Deve estar no 2º andar
    }

    @Test
    void testSubirLimite() {
        for (int i = 0; i < totalAndares; i++) {
            assertTrue(elevador.subir());
        }
        assertFalse(elevador.subir()); // Não pode subir mais
    }

    @Test
    void testDescer() {
        elevador.subir();
        elevador.subir();
        assertTrue(elevador.descer());
        assertEquals(1, elevador.getAndarAtual()); // Deve estar no 1º andar
    }

    @Test
    void testDescerLimite() {
        assertFalse(elevador.descer()); // Não pode descer mais
    }

    @Test
    void testEstaAcimaCapacidade() {
        elevador.adicionarPessoa("Alice");
        elevador.adicionarPessoa("Bob");
        elevador.adicionarPessoa("Charlie");
        elevador.adicionarPessoa("Diana");
        elevador.adicionarPessoa("Eve");
        assertFalse(elevador.estaAcimaCapacidade()); // Deve estar dentro da capacidade
        elevador.adicionarPessoa("Frank");
        assertFalse(elevador.estaAcimaCapacidade()); // Deve estar acima da capacidade
    }

    @Test
    void testExibirInformacoes() {
        elevador.adicionarPessoa("Alice");
        elevador.adicionarPessoa("Bob");
        elevador.subir();
        elevador.exibirInformacoes();
        assertEquals(1, elevador.getAndarAtual()); // Deve estar no 1º andar
        assertEquals(2, elevador.getPessoas().size()); // Deve ter 2 pessoas
    }
}