//
//  ViewListeDeroulanteExamenThematique.h
//  Permis Bateau
//
//  Created by Alexandre Dubois on 16/11/2014.
//  Copyright (c) 2014 Alexandre Dubois. All rights reserved.
//


#import <UIKit/UIKit.h>
#import "ViewQuestionnaire.h"
#import "Serie.h"




@interface ViewListeDeroulanteSerie : UITableViewController
@property (weak, nonatomic) IBOutlet UINavigationItem *navigation;

@property (strong, nonatomic) NSFetchedResultsController *fetchedResultsController;
@property (nonatomic,strong) NSManagedObjectContext* managedObjectContext;

@property (nonatomic,strong) NSArray* listeSeries;

@property (strong, nonatomic) Theme *themeSelectionne;
@property (strong, nonatomic) Theme *theme;

@property (nonatomic, assign) BOOL examenThematique;


@end


