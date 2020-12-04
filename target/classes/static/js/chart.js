$(function() {

  var dataChart = [];
  var labelChart = [];

  for(var i=0;i<vm.chartDataVMS.length;i++) {
    dataChart.push(vm.chartDataVMS[i].value);
    labelChart.push(vm.chartDataVMS[i].label);
  }
  var doughnutPieData = {

    datasets: [{
      data: dataChart,
      backgroundColor: [
        'rgba(255, 99, 132, 0.5)',
        'rgba(54, 162, 235, 0.5)',
        'rgba(255, 206, 86, 0.5)',
        'rgba(75, 192, 192, 0.5)',
        'rgba(153, 102, 255, 0.5)',
        'rgba(255, 159, 64, 0.5)'
      ],
      borderColor: [
        'rgba(255,99,132,1)',
        'rgba(54, 162, 235, 1)',
        'rgba(255, 206, 86, 1)',
        'rgba(75, 192, 192, 1)',
        'rgba(153, 102, 255, 1)',
        'rgba(255, 159, 64, 1)'
      ],
    }],

    labels: labelChart
  };
  var doughnutPieOptions = {
    responsive: true,
    animation: {
      animateScale: true,
      animateRotate: true
    }
  };
  if ($("#barChart").length) {
    var barChartCanvas = $("#barChart").get(0).getContext("2d");
    var barChart = new Chart(barChartCanvas, {
      type: 'bar',
      data: data,
      options: options
    });
  }
  if ($("#doughnutChart").length) {

    var doughnutChartCanvas = $("#doughnutChart").get(0).getContext("2d");
    var doughnutChart = new Chart(doughnutChartCanvas, {
      type: 'doughnut',
      data: doughnutPieData,
      options: doughnutPieOptions
    });
  }
});