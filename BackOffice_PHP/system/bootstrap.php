<?php
// Configuration
require_once(ROOT . '/config/config.php');

// Autoload
require_once('autoload.php');

// Rooting
$router = new \FSF\Routing\Router();
$router->dispatch(new \FSF\Routing\Request());