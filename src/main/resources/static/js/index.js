document.addEventListener("DOMContentLoaded", () => {
  const links = document.querySelectorAll(".nav-link");
  const tabs = document.querySelectorAll(".tab-content");

  links.forEach((link) => {
    link.addEventListener("click", (event) => {
      event.preventDefault();

      tabs.forEach((tab) => tab.classList.remove("active"));

      const tabId = link.dataset.tab;
      document.getElementById(tabId).classList.add("active");

      history.pushState(null, "", `#${tabId}`);
    });
  });

  const currentTab = window.location.hash.slice(1);
  if (currentTab) {
    document.getElementById(currentTab)?.classList.add("active");
  } else {
    document.getElementById("home").classList.add("active");
  }
});