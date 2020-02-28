$(document).ready(function() {
    $("select").change(function () {
        filterTable(document.getElementById('startList'));
    });
    $(window).load(function () {
        filterTable(document.getElementById('startList'));
    });

    function filterTable($table) {
        var filters = $('select option:selected').text();
        //var $rows = $table.find('.data');
        //$rows.each(function (rowIndex) {
        $("table tbody tr").each(function () {

            var valid = true;
            var col = $(this).find('td:eq(3)').text();
            var $tds = $(this).find('td');

            if (filters.toUpperCase() === col.toUpperCase()) {
                $(this).css('display', '');
            }
            else {
                $(this).css('display', 'none');
            }

        });
    }
});


