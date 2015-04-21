<?php
namespace FSF\Routing;

use FSF\Routing\Route;

class Router
{

    /** @var Route[] List of routes to check on routing */
    private $routes = array();

    /** @var  Request */
    private $originalRequest;

    /**
     * @param Request $request
     *
     * @return $this
     */
    private function setOriginalRequest(Request $request)
    {
        $this->originalRequest = clone $request;

        return $this;
    }

    /**
     * Retrieve original request
     *
     * @return Request
     */
    public function getOriginalRequest()
    {
        return $this->originalRequest;
    }

    /**
     * Clear all routes defined
     *
     * @return $this
     */
    public function clearRoutes()
    {
        $this->routes = array();

        return $this;
    }

    /**
     * Add a route in stack
     *
     * @param Route $route
     *
     * @return $this
     */
    public function addRoute(Route $route)
    {
        array_push($this->routes, $route);

        return $this;
    }
}