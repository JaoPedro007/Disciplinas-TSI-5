var listarProcessos = function(filtros = {}) {
    var url = "/delegacia/api/boletim";
    var queryParams = [];

    if (filtros.identificador) {
        queryParams.push("identificador=" + encodeURIComponent(filtros.identificador));
    }
    if (filtros.cidade) {
        queryParams.push("cidade=" + encodeURIComponent(filtros.cidade));
    }
    if (filtros.periodo) {
        queryParams.push("periodo=" + encodeURIComponent(filtros.periodo));
    }

    if (queryParams.length > 0) {
        url += "?" + queryParams.join("&");
    }

    $.ajax({
        url: url,
        type: 'GET',
        async: true,
        contentType: 'application/json',
        success: function(boletins) {
            $("#loading").hide();
            limparTabela();
            $.each(boletins, function(index, bo) {
                var novaLinha =
                    '<tr>' +
                        '<td style="text-align: center">' + bo.identificador + '</td>' +
                        '<td style="text-align: center">' + bo.dataOcorrencia + '</td>' +
                        '<td style="text-align: center">' + bo.periodoOcorrencia + '</td>' +
                        '<td style="text-align: center">' + bo.partes.nome + '</td>' +
                        '<td style="text-align: center">' + bo.partes.telefone + '</td>' +
                        '<td style="text-align: center">' + bo.partes.email + '</td>' +       
                        '<td style="text-align: center">' + bo.localOcorrencia.logradouro + '</td>' +
                        '<td style="text-align: center">' + bo.localOcorrencia.numero + '</td>' +
                        '<td style="text-align: center">' + bo.localOcorrencia.bairro + '</td>' +
                        '<td style="text-align: center">' + bo.localOcorrencia.cidade + '</td>' +
                        '<td style="text-align: center">' + bo.localOcorrencia.estado + '</td>' +
                        '<td style="text-align: center">' + bo.veiculoFurtado.anoFabricacao + '</td>' +
                        '<td style="text-align: center">' + bo.veiculoFurtado.cor + '</td>' +
                        '<td style="text-align: center">' + bo.veiculoFurtado.marca + '</td>' +
                        '<td style="text-align: center">' + bo.veiculoFurtado.modelo + '</td>' +
						'<td style="text-align: center">' + bo.veiculoFurtado.tipo + '</td>' +
                        '<td style="text-align: center">' + bo.veiculoFurtado.emplacamento.placa + '</td>' +
                        '<td style="text-align: center">' + bo.veiculoFurtado.emplacamento.cidade + '</td>' +
                        '<td style="text-align: center">' + bo.veiculoFurtado.emplacamento.estado + '</td>' +
                        '<td style="text-align: center">' +
                            '<button class="remover-btn" data-id="' + bo.identificador + '">Remover</button>' +
                            '<button class="editar-btn" data-id="' + bo.identificador + '">Editar</button>' +
                        '</td>' +
                    '</tr>';
                $("#tabelaBos tbody").append(novaLinha);
            });
            
            $(".remover-btn").on("click", function() {
                var id = $(this).data("id");
                removerBoletim(id);
            });
            
            $(".editar-btn").on("click", function() {
                var id = $(this).data("id");
                editarBoletim(id);
            });
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.error("Erro na requisição AJAX:", textStatus, errorThrown);
        }
    });
};

var aplicarFiltros = function() {
    var identificadorFiltro = $("#filtroIdentificador").val().trim();
    var cidadeFiltro = $("#filtroCidade").val().trim();
    var periodoFiltro = $("#filtroPeriodo").val().trim();

    var filtros = {
        identificador: identificadorFiltro,
        cidade: cidadeFiltro,
        periodo: periodoFiltro
    };

    listarProcessos(filtros);
};

function editarBoletim(id) {
    window.location.href = '/delegacia/editarBoletim?id=' + id;
}

function removerBoletim(id) {
    if (confirm('Tem certeza que deseja remover este boletim?')) {
        $.ajax({
            url: '/delegacia/api/boletim?id=' + encodeURIComponent(id),
            type: 'DELETE',
            success: function(result) {
                alert(result);
                location.reload();
            },
            error: function(err) {
                alert('Erro ao remover o boletim: ' + err.responseText);
            }
        });
    }
}

var limparTabela = function() {
    $("#tabelaBos tbody").empty();
};

$(document).ready(function() {
    listarProcessos();    
    
    $("#botaoFiltrar").on("click", function() {
        aplicarFiltros();
    });

    $("#botaoLimpar").on("click", function() {
        $("#filtroIdentificador").val('');
        $("#filtroCidade").val('');
        $("#filtroPeriodo").val('');
        listarProcessos();
    });
});
