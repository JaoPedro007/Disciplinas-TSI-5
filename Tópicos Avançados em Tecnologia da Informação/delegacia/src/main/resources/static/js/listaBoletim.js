var listarProcessos = function() {
    $.ajax({
        url: "http://localhost:8080/delegacia/boletim",
        type: 'GET',
        async: true,
        contentType: 'application/json',
        success: function(boletins) {
            $("#loading").hide();
            limparTabela();
            $.each(boletins, function(index, bo) {
                console.log(bo);
                var novaLinha =
                    '<tr>' +
                    '<td style="text-align: center">' + bo.identificador + '</td>' +
                    '<td style="text-align: center">' + bo.dataOcorrencia + '</td>' +
                    '<td style="text-align: center">' + bo.localOcorrencia.cidade + '</td>' +
                    '<td style="text-align: center">' + bo.periodoOcorrencia + '</td>' +
                    '<td style="text-align: center">' + bo.veiculoFurtado.marca + '</td>' +
                    '<td style="text-align: center">' + bo.veiculoFurtado.emplacamento.placa + '</td>' +
                    '<td style="text-align: center">' + bo.veiculoFurtado.cor + '</td>' +
                    '</tr>';
                $("#tabelaBos tbody").append(novaLinha);
            });
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.error("Erro na requisição AJAX:", textStatus, errorThrown);
        }
    });
};

var limparTabela = function() {
    $("#tabelaBos tbody").empty();
};

var consultar = function(urlContato) {
    sessionStorage.setItem("urlContato", urlContato);
    window.location.href = "consulta.html";
};

$(document).ready(function() {
    listarProcessos();    
});
