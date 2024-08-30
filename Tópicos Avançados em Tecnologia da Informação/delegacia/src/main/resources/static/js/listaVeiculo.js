var currentPage = 0;
var pageSize = 10;

function limparTabela() {
    $("#tabelaBos tbody").empty();
}

function atualizarPaginaAtual() {
    $("#paginaAtual").text("Página " + (currentPage + 1));
}

function aplicarFiltros() {
    var placaFiltro = $("#filtroPlaca").val().trim();
    var corFiltro = $("#filtroCor").val().trim();
    var tipoFiltro = $("#filtroTipo").val().trim();

    var filtros = {
        placa: placaFiltro,
        cor: corFiltro,
        tipo: tipoFiltro
    };

    listarProcessos(filtros, currentPage, pageSize);
}

var listarProcessos = function(filtros = {}, page = 0, size = 10) {
    var url = "/delegacia/api/veiculo";
    var queryParams = [];

    if (filtros.placa) {
        queryParams.push("placa=" + encodeURIComponent(filtros.placa));
    }
    if (filtros.cor) {
        queryParams.push("cor=" + encodeURIComponent(filtros.cor));
    }
    if (filtros.tipo) {
        queryParams.push("tipo=" + encodeURIComponent(filtros.tipo));
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
        success: function(veiculos) {
            $("#loading").hide();
            limparTabela();
            $.each(veiculos, function(index, veiculo) {
                var novaLinha =
                    '<tr>' +
                        '<td style="text-align: center">' + veiculo.anoFabricacao + '</td>' +
                        '<td style="text-align: center">' + veiculo.cor + '</td>' +
                        '<td style="text-align: center">' + veiculo.marca + '</td>' +
                        '<td style="text-align: center">' + veiculo.tipo + '</td>' +
                        '<td style="text-align: center">' + veiculo.emplacamento.placa + '</td>' +
                        '<td style="text-align: center">' + veiculo.emplacamento.cidade + '</td>' +
                        '<td style="text-align: center">' + veiculo.emplacamento.estado + '</td>' +
                    '</tr>';
                $("#tabelaBos tbody").append(novaLinha);
            });

            atualizarPaginaAtual();
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.error("Erro na requisição AJAX:", textStatus, errorThrown);
        }
    });
};

$(document).ready(function() {
    listarProcessos({}, currentPage, pageSize);

    $("#botaoFiltrar").on("click", function() {
        currentPage = 0;
        aplicarFiltros();
    });

    $("#botaoLimpar").on("click", function() {
        $("#filtroPlaca").val('');
        $("#filtroCor").val('');
        $("#filtroTipo").val('');
        listarProcessos({}, currentPage, pageSize);
    });

    $("#botaoProximaPagina").on("click", function() {
        currentPage++;
        aplicarFiltros();
    });

    $("#botaoPaginaAnterior").on("click", function() {
        if (currentPage > 0) {
            currentPage--;
            aplicarFiltros();
        }
    });
});
