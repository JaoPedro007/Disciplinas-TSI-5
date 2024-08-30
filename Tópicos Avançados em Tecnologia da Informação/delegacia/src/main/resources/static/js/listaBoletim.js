var currentPage = 0;
var pageSize = 10;

function limparTabela() {
    $("#tabelaBos tbody").empty();
}

function atualizarPaginaAtual() {
    $("#paginaAtual").text("Página " + (currentPage + 1));
}

function aplicarFiltros() {
    var filtros = {
        identificador: $("#filtroIdentificador").val(),
        cidade: $("#filtroCidade").val(),
        periodo: $("#filtroPeriodo").val()
    };
    listarProcessos(filtros, currentPage, pageSize);
}

var listarProcessos = function(filtros = {}, page = 0, size = 10) {
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

    queryParams.push("page=" + page);
    queryParams.push("size=" + size);

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
                var partesNome = bo.partes ? bo.partes.nome : '';
                var partesTelefone = bo.partes ? bo.partes.telefone : '';
                var partesEmail = bo.partes ? bo.partes.email : '';

                var novaLinha =
                    '<tr>' +
                        '<td style="text-align: center">' + bo.identificador + '</td>' +
                        '<td style="text-align: center">' + bo.dataOcorrencia + '</td>' +
                        '<td style="text-align: center">' + bo.periodoOcorrencia + '</td>' +
                        '<td style="text-align: center">' + partesNome + '</td>' +
                        '<td style="text-align: center">' + partesTelefone + '</td>' +
                        '<td style="text-align: center">' + partesEmail + '</td>' +
                        '<td style="text-align: center">' + bo.localOcorrencia.logradouro + '</td>' +
                        '<td style="text-align: center">' + bo.localOcorrencia.numero + '</td>' +
                        '<td style="text-align: center">' + bo.localOcorrencia.bairro + '</td>' +
                        '<td style="text-align: center">' + bo.localOcorrencia.cidade + '</td>' +
                        '<td style="text-align: center">' + bo.localOcorrencia.estado + '</td>' +
                        '<td style="text-align: center">' + bo.veiculoFurtado.anoFabricacao + '</td>' +
                        '<td style="text-align: center">' + bo.veiculoFurtado.cor + '</td>' +
                        '<td style="text-align: center">' + bo.veiculoFurtado.marca + '</td>' +
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

            atualizarPaginaAtual();

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

function editarBoletim(id) {
    window.location.href = '/delegacia/editarBoletim?identificador=' + id;
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

$(document).ready(function() {
    listarProcessos({}, currentPage, pageSize);

    $("#botaoFiltrar").on("click", function() {
        currentPage = 0;
        aplicarFiltros();
    });

    $("#botaoLimpar").on("click", function() {
        $("#filtroIdentificador").val('');
        $("#filtroCidade").val('');
        $("#filtroPeriodo").val('');
        listarProcessos({}, currentPage, pageSize);
    });

    $("#botaoProximaPagina").on("click", function() {
        currentPage++;
        listarProcessos({
            identificador: $("#filtroIdentificador").val(),
            cidade: $("#filtroCidade").val(),
            periodo: $("#filtroPeriodo").val()
        }, currentPage, pageSize);
    });

    $("#botaoPaginaAnterior").on("click", function() {
        if (currentPage > 0) {
            currentPage--;
            listarProcessos({
                identificador: $("#filtroIdentificador").val(),
                cidade: $("#filtroCidade").val(),
                periodo: $("#filtroPeriodo").val()
            }, currentPage, pageSize);
        }
    });
});
