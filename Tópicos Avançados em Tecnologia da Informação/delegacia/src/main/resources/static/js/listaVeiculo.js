var listarProcessos = function(filtros = {}) {
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
				console.log(veiculo);
                var novaLinha =
                    '<tr>' +
                        '<td style="text-align: center">' + veiculo.anoFabricacao + '</td>' +
                        '<td style="text-align: center">' + veiculo.cor + '</td>' +
                        '<td style="text-align: center">' + veiculo.marca + '</td>' +
                        '<td style="text-align: center">' + veiculo.modelo + '</td>' +
						'<td style="text-align: center">' + veiculo.tipo + '</td>' +
                        '<td style="text-align: center">' + veiculo.emplacamento.placa + '</td>' +
                        '<td style="text-align: center">' + veiculo.emplacamento.cidade + '</td>' +
                        '<td style="text-align: center">' + veiculo.emplacamento.estado + '</td>' +
                    '</tr>';
                $("#tabelaBos tbody").append(novaLinha);
            });
            
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.error("Erro na requisição AJAX:", textStatus, errorThrown);
        }
    });
};

var aplicarFiltros = function() {
    var placaFiltro = $("#filtroPlaca").val().trim();
    var corFiltro = $("#filtroCor").val().trim();
    var tipoFiltro = $("#filtroTipo").val().trim();

    var filtros = {
        placa: placaFiltro,
        cor: corFiltro,
        tipo: tipoFiltro
    };

    listarProcessos(filtros);
};


var limparTabela = function() {
    $("#tabelaBos tbody").empty();
};

$(document).ready(function() {
    listarProcessos();    
    
    $("#botaoFiltrar").on("click", function() {
        aplicarFiltros();
    });

    $("#botaoLimpar").on("click", function() {
        $("#filtroPlaca").val('');
        $("#filtroCor").val('');
        $("#filtroTipo").val('');
        listarProcessos();
    });
});
