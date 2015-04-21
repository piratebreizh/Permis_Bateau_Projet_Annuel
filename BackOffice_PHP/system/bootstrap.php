<?php
// Configuration
require_once(ROOT . '/config/config.php');

// Autoload
require_once('autoload.php');

// Rooting
$framework = new \FSF\Framework();
$framework
    ->setRouter(new FSF\Routing\Router())
    ->initialize();