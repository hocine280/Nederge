<!-- Début session -->
<?php session_start(); ?>
<!DOCTYPE html>
<html lang="fr">
  <head>
    <meta charset="utf-8">
    <meta content="width=device-width, initial-scale=1.0" name="viewport">

    <title>Nederge - Système d'achat d'énergie</title>

    <!-- Typo -->
    <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i|Roboto:300,300i,400,400i,500,500i,600,600i,700,700i|Poppins:300,300i,400,400i,500,500i,600,600i,700,700i" rel="stylesheet">

    <!-- Fichier CSS -->
    <link href="public/vendor/aos/aos.css" rel="stylesheet">
    <link href="public/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="public/vendor/bootstrap-icons/bootstrap-icons.css" rel="stylesheet">
    <link href="public/css/style.css" rel="stylesheet">

  </head>

  <body>

    <!-- ======= Header ======= -->
    <header id="header" class="fixed-top ">
      <div class="container d-flex align-items-center justify-content-between">
        <h1 class="logo"><a href="/">N E D E R G E</a></h1>

        <!-- ======== Navbar ======== -->
        <nav id="navbar" class="navbar">
          <ul>
            <li><a class="nav-link scrollto active" href="#hero">Accueil</a></li>
            <li><a class="nav-link scrollto" href="#about">A propos</a></li>
            <li><a class="nav-link scrollto" href="#features">Fonctionnalités</a></li>
            <li><a class="nav-link scrollto" href="resources/views/ScenarioView.php">Mode scénario</a></li>
            <li class="dropdown"><a href="#"><span>Mon énergie</span> <i class="bi bi-chevron-down"></i></a>
              <ul>
                <li><a href="resources/views/OrderEnergyView.php">Commander une énergie</a></li>
                <li><a href="resources/views/ConsultAvailableEnergyView.php">Consulter les énergies disponibles</a></li>
                <li><a href="resources/views/MyOrderView.php">Mes commandes</a></li>
              </ul>
            </li>
            <li><a class="getstarted scrollto" href="#about">Découvrir</a></li>
          </ul>
          <i class="bi bi-list mobile-nav-toggle"></i>
        </nav>

      </div>
    </header>

    <!-- ======= Hero ======= -->
    <section id="hero" class="d-flex align-items-center">

      <div class="container-fluid" data-aos="fade-up">
        <div class="row justify-content-center">
          <div class="col-xl-5 col-lg-6 pt-3 pt-lg-0 order-2 order-lg-1 d-flex flex-column justify-content-center">
            <h1 id="bloc"></h1>
            <h2>Nederge, un système d'achat d'énergie pas comme les autres ! <br>Achetez votre énergie en toute confiance !</h2>
            <div><a href="#about" class="btn-get-started scrollto">Découvrir</a></div>
          </div>
          <div class="col-xl-4 col-lg-6 order-1 order-lg-2 hero-img" data-aos="zoom-in" data-aos-delay="150">
            <img src="public/img/trader.png" class="img-fluid animated" alt="">
          </div>
        </div>
      </div>

    </section>

    <main id="main">

      <!-- ======= A propos ======= -->
      <section id="about" class="about">
        <div class="container">
          <div class="row">
            <div class="col-lg-6 order-1 order-lg-2 text-center" data-aos="zoom-in" data-aos-delay="150">
              <img src="public/img/energy.png" class="img-fluid" alt="">
            </div>
            <div class="col-lg-6 pt-4 pt-lg-0 order-2 order-lg-1 content" data-aos="fade-right">
              <h3>Nederge ? Qu'est-ce que c'est ?</h3>
              <p class="fst-italic">
                Nederge est une plateforme d'achat d'énergie vous simplifiant l'achat de votre énergie.
              </p>
              <ul>
                <li class="text-propos">
                  <i class="bi bi-currency-dollar icon"></i>
                  Nous garantissons de trouver les meilleurs prix pour vous !
                </li>
                <li class="mt-3 text-propos">
                  <i class="bi bi-hourglass-split icon"></i>
                  N'attendez plus des semaines avant de recevoir votre commande, 
                  Nederge s'engage à vous répondre dans les heures qui suivent votre commande.
                </li>
                <li class="mt-3 text-propos">
                  <i class="bi bi-lightning-fill icon"></i> 
                  Nous vous proposons une large gamme d'énergie pour répondre à tous vos besoins.
                </li>
              </ul>
              <a href="#features" class="read-more">Voir plus <i class="bi bi-plus-circle"></i></a>
            </div>
          </div>

        </div>
      </section>

      <!-- ======= Compteur chiffre ======= -->
      <section id="counts" class="counts">
        <div class="container">

          <div class="row counters">

            <div class="col-lg-3 col-6 text-center">
              <span data-purecounter-start="0" data-purecounter-end="268" data-purecounter-duration="1" class="purecounter"></span>
              <p>Clients</p>
            </div>

            <div class="col-lg-3 col-6 text-center">
              <span data-purecounter-start="0" data-purecounter-end="14" data-purecounter-duration="1" class="purecounter"></span>
              <p>Producteur - PONEs</p>
            </div>

            <div class="col-lg-3 col-6 text-center">
              <span data-purecounter-start="0" data-purecounter-end="26" data-purecounter-duration="1" class="purecounter"></span>
              <p>Trader - TARE</p>
            </div>

            <div class="col-lg-3 col-6 text-center">
              <span data-purecounter-start="0" data-purecounter-end="1" data-purecounter-duration="1" class="purecounter"></span>
              <p>AMI</p>
            </div>

          </div>

        </div>
      </section>

      <!-- ======= Fonctionnalités ======= -->
      <section id="features" class="features">
        <div class="container" data-aos="fade-up">

          <div class="section-title">
            <h2>Fonctionnalités</h2>
            <p>Nederge vous offre de nombreuses fonctionnalités dont vous profitez pleinement à la suite d'une commande d'énergie</p>
          </div>

          <div class="row">
            <div class="col-lg-6 order-2 order-lg-1 d-flex flex-column align-items-lg-center">
              <div class="icon-box mt-5 mt-lg-0" data-aos="fade-up" data-aos-delay="100">
                <i class="bi bi-check2-circle"></i>
                <h4>Choississez l'énergie qui vous convient le plus !</h4>
                <p>Vous souhaitez une énergie verte, un mode d'extraction spécifique ou encore la provenance de votre énergie, Nederge vous laisse le choix !</p>
              </div>
              <div class="icon-box mt-5" data-aos="fade-up" data-aos-delay="200">
                <i class="bi bi-credit-card-fill"></i>
                <h4>Payer en toute confiance !</h4>
                <p>Grâce à un système de paiement utilisant les dernières technologies, payer en toute tranquilité.</p>
              </div>
              <div class="icon-box mt-5 m-left" data-aos="fade-up" data-aos-delay="300">
                <i class="bi bi-truck"></i>
                <h4>Suivez votre commande !</h4>
                <p>Suivez votre commande en temps réel pour savoir où en est votre énergie </p>
              </div>
              <div class="icon-box mt-5 m-command" data-aos="fade-up" data-aos-delay="300">
                <i class="bi bi-cart-check-fill"></i>
                <h4>Plus qu'à commander !</h4>
                <p> Découvrer notre plateforme en passant votre première commande !</p>
              </div>

            </div>
            <div class="image col-lg-6 order-1 order-lg-2 " data-aos="zoom-in" data-aos-delay="100">
              <img src="public/img/echange.png" alt="" class="img-fluid">
            </div>
          </div>
          <div class="row mt-5">
            <div class="col-md-12 text-center">
              <a class="command" href="resources/views/OrderEnergyView.php">Passez votre première commande !</a>
            </div>
          </div>

        </div>
      </section>

    </main>

    <!-- ======= Footer ======= -->
    <?php include 'resources/layout/Footer.html'; ?>

    <a href="#" class="back-to-top d-flex align-items-center justify-content-center"><i class="bi bi-arrow-up-short"></i></a>
    <div id="preloader"></div>

    <!-- Fichier js -->
    <script src="public/vendor/purecounter/purecounter.js"></script>
    <script src="public/vendor/aos/aos.js"></script>
    <script src="public/vendor/glightbox/js/glightbox.min.js"></script>
    <script src="public/vendor/swiper/swiper-bundle.min.js"></script>
    <script src="public/js/main.js"></script>
    <script src="public/js/index.js"></script>

  </body>
</html>