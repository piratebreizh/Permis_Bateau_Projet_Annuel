<?php

namespace APP\Routing\Router;

use APP\Routing\Route as Route;
use FSF\Routing\Router;

class Back extends Router
{
    public function __construct()
    {
        $this->addRoute(new Route\Index());
        $this->addRoute(new Route\WebService());
        $this->addRoute(new Route\Theme());
        $this->addRoute(new Route\Examen());
        $this->addRoute(new Route\Question());
        $this->addRoute(new Route\Cours());
        parent::__construct();
    }
}