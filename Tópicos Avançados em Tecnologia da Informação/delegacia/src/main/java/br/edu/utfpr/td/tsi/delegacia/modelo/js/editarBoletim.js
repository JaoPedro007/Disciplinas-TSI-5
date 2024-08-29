$(document).ready(function() {
    const urlParams = new URLSearchParams(window.location.search);
    const id = urlParams.get('id');

    if (id) {
        $.ajax({
            url: '/delegacia/api/boletim?id=' + encodeURIComponent(id),
            type: 'GET',
            success: function(boletim) {
                $('#identificador').val(boletim.identificador);
                $('#data').val(boletim.dataOcorrencia);
                $('#periodo').val(boletim.periodoOcorrencia);
                $('#logradouro').val(boletim.localOcorrencia.logradouro);
                $('#numero').val(boletim.localOcorrencia.numero);
                $('#bairro').val(boletim.localOcorrencia.bairro);
                $('#cidade').val(boletim.localOcorrencia.cidade);
                $('#estado').val(boletim.localOcorrencia.estado);
                $('#placa').val(boletim.veiculoFurtado.emplacamento.placa);
                $('#estadoPlaca').val(boletim.veiculoFurtado.emplacamento.estado);
                $('#cidadePlaca').val(boletim.veiculoFurtado.emplacamento.cidade);
                $('#cor').val(boletim.veiculoFurtado.cor);
                $('#marca').val(boletim.veiculoFurtado.marca);
                $('#modelo').val(boletim.veiculoFurtado.modelo);
				$('#tipo').val(boletim.veiculoFurtado.tipo);
                $('#anoFabricacao').val(boletim.veiculoFurtado.anoFabricacao);
                $('#nome').val(boletim.partes.nome);
                $('#email').val(boletim.partes.email);
                $('#telefone').val(boletim.partes.telefone);
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
                modelo: $('#modelo').val(),
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
            success: function(result) {
                alert(result);
                location.href = '/delegacia/listaBoletim';
            },
            error: function(err) {
                console.log("Erro ao atualizar o boletim:", err);
                alert('Erro ao atualizar o boletim.');
            }
        });
    });
});
