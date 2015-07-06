//
//  ViewListeDeroulanteExamenThematique.h
//  Permis Bateau
//
//  Created by Alexandre Dubois on 16/11/2014.
//  Copyright (c) 2014 Alexandre Dubois. All rights reserved.
//


#import <UIKit/UIKit.h>



@interface ViewListeDeroulanteMenu : UITableViewController

@property (weak, nonatomic) IBOutlet UIButton *boutonInformation;
@property (weak, nonatomic) IBOutlet UINavigationItem *navigation;

@property (nonatomic,strong) NSArray* listeMenu;


@end


