package com.br.cobranca.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.br.cobranca.model.StatusTitulo;
import com.br.cobranca.model.Titulo;
import com.br.cobranca.repository.Titulos;
import com.br.cobranca.repository.filter.TituloFilter;

@Service
public class CadastroTituloService {
	
	@Autowired
	private Titulos titulos;

	public void salvar(Titulo titulo) {
		try {
			titulos.save(titulo);
		} catch(DataIntegrityViolationException e) {
			throw new IllegalArgumentException("Formato de data inválido");
		}
	}
	
	public void exlcuir(Long codigo) {
		titulos.deleteById(codigo);		
	}

	public String receber(Long codigo) {
		Titulo titulo = titulos.getById(codigo);
		titulo.setStatus(StatusTitulo.RECEBIDO);
		titulos.save(titulo);
		
		return StatusTitulo.RECEBIDO.getDescricao();
	}
	
	public List<Titulo> filtrar(TituloFilter filtro){
		String descricao = filtro.getDescricao() == null ? "%" : filtro.getDescricao(); 
		
		return titulos.findByDescricaoContaining(descricao);
	}
}
