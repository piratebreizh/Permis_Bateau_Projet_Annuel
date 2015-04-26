<?php

namespace APP\Routing\Router;

use APP\Routing\Route as Route;
use FSF\Routing\Router;

class Back extends Router
{
    public function __construct()
    {
        $this->addRoute(new Route\Question());
        parent::__construct();
    }
}