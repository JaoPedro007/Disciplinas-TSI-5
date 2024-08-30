var cadastrar = function(url) {
  var bo = {
	dataOcorrencia: $("#data").val(),
	periodoOcorrencia: $("#periodo").val(),
	localOcorrencia: {
		logradouro: $("#logradouro").val(),
		numero: $("#numero").val(),
		bairro: $("#bairro").val(),
		cidade: $("#cidade").val(),
		estado: $("#estado").val()			
	},
	veiculoFurtado:{
		emplacamento: {
			placa: $("#placa").val(),
			cidade: $("#cidadePlaca").val(),
			estado: $("#estadoPlaca").val()				
		},
		anoFabricacao: $("#anoFabricacao").val(),	
		cor: $("#cor").val(),	
		marca: $("#marca").val(),	
		tipo: $("#tipo").val()	
	
	},
	partes: {
		nome: $("#nome").val(),
		email: $("#email").val(),
		telefone: $("#telefone").val(),
		tipoEnvolvimento: "Vítima"			
	}		
  };
  
  $.ajax({
    url: url,
    type: 'POST',
    async: true,
    contentType: 'application/json',
    data: JSON.stringify(bo),
    success: function(identificador) {
      $("#resultado").empty();
      $("#resultado").html("Boletim cadastrado com sucesso!").css({
		"color": "green",
		"font-weight": "bold"
	  });
	  $("#identificador").html("Identificador: " + identificador);
	  limparCampos();
    },
    error: function(xhr, status, error) {
	  $("#identificador").val('');
      $("#resultado").empty();
      $("#resultado").append("Erro ao cadastrar: " + xhr.responseText).css({
		"color": "red",
		"font-weight": "bold"
	  })
    }
  });
};

	function limparCampos() {
	  $("#data").val('');
	  $("#periodo").val('');
	  $("#logradouro").val('');
	  $("#numero").val('');
	  $("#bairro").val('');
	  $("#cidade").val('');
	  $("#estado").val('');
	  $("#placa").val('');
	  $("#cidadePlaca").val('');
	  $("#estadoPlaca").val('');
	  $("#anoFabricacao").val('');
	  $("#cor").val('');
	  $("#marca").val('');
	  $("#tipo").val('');
	  $("#nome").val('');
	  $("#email").val('');
	  $("#telefone").val('');	  
	}


$(document).ready(function() {
  $("#botaoCadastrar").click(function() {
    cadastrar("http://localhost:8080/delegacia/api/boletim");
  }); 
});
