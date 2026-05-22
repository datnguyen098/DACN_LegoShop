var optionsProfileVisit = {
	annotations: { position: 'back' },
	dataLabels: { enabled: false },
	chart: { type: 'bar', height: 300 },
	fill: { opacity: 1 },
	series: [{ name: 'sales', data: [9, 20, 30, 20, 10, 20, 30, 20, 10, 20, 30, 20] }],
	colors: '#435ebe',
	xaxis: {
		categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],
	},
};

var optionsVisitorsProfile = {
	series: [70, 30],
	labels: ['Nam', 'Nữ'],
	colors: ['#435ebe', '#55c6e8'],
	chart: { type: 'donut', width: '100%', height: '320px' },
	legend: { position: 'bottom' },
	plotOptions: { pie: { donut: { size: '30%' } } },
};

var optionsEurope = {
	series: [{ name: 'series1', data: [310, 800, 600, 430, 540, 340, 605, 805, 430, 540, 340, 605] }],
	chart: { height: 80, type: 'area', toolbar: { show: false } },
	colors: ['#5350e9'],
	stroke: { width: 2 },
	grid: { show: false },
	dataLabels: { enabled: false },
	xaxis: {
		type: 'datetime',
		categories: [
			'2018-09-19T00:00:00.000Z', '2018-09-19T01:30:00.000Z', '2018-09-19T02:30:00.000Z',
			'2018-09-19T03:30:00.000Z', '2018-09-19T04:30:00.000Z', '2018-09-19T05:30:00.000Z',
			'2018-09-19T06:30:00.000Z', '2018-09-19T07:30:00.000Z', '2018-09-19T08:30:00.000Z',
			'2018-09-19T09:30:00.000Z', '2018-09-19T10:30:00.000Z', '2018-09-19T11:30:00.000Z',
		],
		axisBorder: { show: false },
		axisTicks: { show: false },
		labels: { show: false },
	},
	show: false,
	yaxis: { labels: { show: false } },
	tooltip: { x: { format: 'dd/MM/yy HH:mm' } },
};

var optionsAmerica = { ...optionsEurope, colors: ['#008b75'] };
var optionsIndonesia = { ...optionsEurope, colors: ['#dc3545'] };

window._dashboardCharts = window._dashboardCharts || {};

function destroyDashboardCharts() {
	Object.keys(window._dashboardCharts).forEach(function (key) {
		try {
			if (window._dashboardCharts[key]) {
				window._dashboardCharts[key].destroy();
			}
		} catch (e) { /* ignore */ }
		delete window._dashboardCharts[key];
	});
}

window.initDashboardCharts = function () {
	if (typeof ApexCharts === 'undefined') return;
	var elVisit = document.querySelector('#chart-profile-visit');
	if (!elVisit) return;

	destroyDashboardCharts();

	window._dashboardCharts.profileVisit = new ApexCharts(elVisit, optionsProfileVisit);

	var elVisitors = document.getElementById('chart-visitors-profile');
	if (elVisitors) {
		window._dashboardCharts.visitorsProfile = new ApexCharts(elVisitors, optionsVisitorsProfile);
	}
	var elEurope = document.querySelector('#chart-europe');
	if (elEurope) {
		window._dashboardCharts.europe = new ApexCharts(elEurope, optionsEurope);
	}
	var elAmerica = document.querySelector('#chart-america');
	if (elAmerica) {
		window._dashboardCharts.america = new ApexCharts(elAmerica, optionsAmerica);
	}
	var elIndonesia = document.querySelector('#chart-indonesia');
	if (elIndonesia) {
		window._dashboardCharts.indonesia = new ApexCharts(elIndonesia, optionsIndonesia);
	}

	Object.values(window._dashboardCharts).forEach(function (c) {
		if (c) c.render();
	});
};

if (document.querySelector('#chart-profile-visit')) {
	window.initDashboardCharts();
}
