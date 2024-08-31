$(document).ready(function() {
    const urlParams = new URLSearchParams(window.location.search);
    const id = urlParams.get('identificador');

    if (id) {
        $.ajax({
            url: '/delegacia/api/boletim?identificador=' + encodeURIComponent(id),
            type: 'GET',
			success: function(data) {
				const boletim  = data[0];
			    $('#identificador').val(boletim.identificador || '');
			    $('#data').val(boletim.dataOcorrencia || '');
			    $('#periodo').val(boletim.periodoOcorrencia || '');

			    $('#logradouro').val(boletim.localOcorrencia ? boletim.localOcorrencia.logradouro : '');
			    $('#numero').val(boletim.localOcorrencia ? boletim.localOcorrencia.numero : '');
			    $('#bairro').val(boletim.localOcorrencia ? boletim.localOcorrencia.bairro : '');
			    $('#cidade').val(boletim.localOcorrencia ? boletim.localOcorrencia.cidade : '');
			    $('#estado').val(boletim.localOcorrencia ? boletim.localOcorrencia.estado : '');

			    $('#placa').val(boletim.veiculoFurtado ? boletim.veiculoFurtado.emplacamento.placa : '');
			    $('#estadoPlaca').val(boletim.veiculoFurtado ? boletim.veiculoFurtado.emplacamento.estado : '');
			    $('#cidadePlaca').val(boletim.veiculoFurtado ? boletim.veiculoFurtado.emplacamento.cidade : '');
			    $('#cor').val(boletim.veiculoFurtado ? boletim.veiculoFurtado.cor : '');
			    $('#marca').val(boletim.veiculoFurtado ? boletim.veiculoFurtado.marca : '');
			    $('#tipo').val(boletim.veiculoFurtado ? boletim.veiculoFurtado.tipo : '');
			    $('#anoFabricacao').val(boletim.veiculoFurtado ? boletim.veiculoFurtado.anoFabricacao : '');

			    $('#nome').val(boletim.partes ? boletim.partes.nome : '');
			    $('#email').val(boletim.partes ? boletim.partes.email : '');
			    $('#telefone').val(boletim.partes ? boletim.partes.telefone : '');
			},
            error: function(err) {
                console.log("Erro ao carregar o boletim:", err);
                alert('Erro ao carregar os dados do boletim.');
            }
        });
    }

    $('#botaoSalvar').on('click', function() {
        const boletim = {
            identificador: $('#identificador').val(),
            dataOcorrencia: $('#data').val(),
            periodoOcorrencia: $('#periodo').val(),
            localOcorrencia: {
                logradouro: $('#logradouro').val(),
                numero: $('#numero').val(),
                bairro: $('#bairro').val(),
                cidade: $('#cidade').val(),
                estado: $('#estado').val()
            },
            veiculoFurtado: {
                emplacamento: {
                    placa: $('#placa').val(),
                    estado: $('#estadoPlaca').val(),
                    cidade: $('#cidadePlaca').val()
                },
                cor: $('#cor').val(),
                marca: $('#marca').val(),
				tipo: $('#tipo').val(),
                anoFabricacao: $('#anoFabricacao').val()
            },
            partes: {
                nome: $('#nome').val(),
                email: $('#email').val(),
                telefone: $('#telefone').val()
            }
        };

        $.ajax({
            url: '/delegacia/api/boletim?id=' + encodeURIComponent(boletim.identificador),
            type: 'PUT',
            contentType: 'application/json',
            data: JSON.stringify(boletim),
			success: function() {
			  $("#resultado").empty();
			  $("#resultado").html("Boletim cadastrado com sucesso!").css({
				"color": "green",
				"font-weight": "bold"
			  });
			},
			error: function(xhr, status, error) {
			  $("#resultado").empty();
			  $("#resultado").append("Erro ao cadastrar: " + xhr.responseText).css({
				"color": "red",
				"font-weight": "bold"
			  })
			}
        });
    });
});
