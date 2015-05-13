<?php
// Configuration
require_once(ROOT . '/config/config.php');

date_default_timezone_set("Europe/Paris");

// Autoload
require_once('autoload.php');

// Rooting
$framework = new \FSF\Framework();
$framework
    ->setRouter(new \APP\Routing\Router\Back())
    ->initialize();