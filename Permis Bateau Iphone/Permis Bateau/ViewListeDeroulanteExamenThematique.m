//
//  ViewListeDeroulanteExamenThematique.m
//  Permis Bateau
//
//  Created by Alexandre Dubois on 16/11/2014.
//  Copyright (c) 2014 Alexandre Dubois. All rights reserved.
//

#import "ViewListeDeroulanteExamenThematique.h"

@interface ViewListeDeroulanteExamenThematique()

@end

@implementation ViewListeDeroulanteExamenThematique
@synthesize managedObjectContext;



- (id)initWithStyle:(UITableViewStyle)style
{
    self = [super initWithStyle:style];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    
    
    
    // Add padding to the top of the table view
    UIEdgeInsets inset = UIEdgeInsetsMake(5, 0, 0, 0);
    self.tableView.contentInset = inset;
    [self.tableView setBackgroundColor:[UIColor colorWithRed:213/255.0f green:230/255.0f blue:245/255.0f alpha:1.0f]];
    [self.tableView setSeparatorStyle:UITableViewCellSeparatorStyleNone];

    
    id delegate = [[UIApplication sharedApplication] delegate];
     self.managedObjectContext = [delegate managedObjectContext];
     
     NSFetchRequest *fetchRequest = [[NSFetchRequest alloc] init];
    
     NSEntityDescription *entity = [NSEntityDescription
     entityForName:@"Theme" inManagedObjectContext:self.managedObjectContext];

    NSSortDescriptor *sortDescriptor = [[NSSortDescriptor alloc] initWithKey:@"numero" ascending:YES];

    
     [fetchRequest setEntity:entity];
     [fetchRequest setSortDescriptors:@[sortDescriptor]];
     NSError *error;
     self.listeThemes = [managedObjectContext executeFetchRequest:fetchRequest error:&error];
    

    // Assign our own backgroud for the view
    /*self.parentViewController.view.backgroundColor = [UIColor colorWithPatternImage:[UIImage imageNamed:@"common_bg"]];
    self.tableView.backgroundColor = [UIColor clearColor];*/
    
    
    
     /*for(Theme *tempTheme in self.listeThemes){
         NSLog([NSString stringWithFormat:tempTheme.nom]);
     }*/
}

- (void)viewDidUnload
{
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}

#pragma mark - Table view data source

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    // Return the number of sections.
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    // Return the number of rows in the section.
    return [self.listeThemes count];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
   static NSString *CellIdentifier = @"Cell";
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier];
    
    if (!cell) {
        cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleSubtitle reuseIdentifier:CellIdentifier];
    }
    
    // Configure the cell...
    Theme *info = [self.listeThemes objectAtIndex:indexPath.row];
    //cell.textLabel.text = info.nom;
    cell.detailTextLabel.text = [NSString stringWithFormat:@"%@", info.numero];
    NSLog(@"%@", info.id);
    
    UILabel *recipeNameLabel = (UILabel *)[cell viewWithTag:101];

    
    
    UIImage *background = [self cellBackgroundForRowAtIndexPath:indexPath];
    
    UIImageView *cellBackgroundView = [[UIImageView alloc] initWithImage:background];
    cell.backgroundColor = [UIColor clearColor];
    cellBackgroundView.image = background;
    cell.backgroundView = cellBackgroundView;
    
    recipeNameLabel.text = info.nom;
    
    
    return cell;
}



#pragma mark - Table view delegate

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    // Navigation logic may go here. Create and push another view controller.
    /*
     <#DetailViewController#> *detailViewController = [[<#DetailViewController#> alloc] initWithNibName:@"<#Nib name#>" bundle:nil];
     // ...
     // Pass the selected object to the new view controller.
     [self.navigationController pushViewController:detailViewController animated:YES];
     */
}


- (UIImage *)cellBackgroundForRowAtIndexPath:(NSIndexPath *)indexPath
{
    NSInteger rowCount = [self tableView:[self tableView] numberOfRowsInSection:0];
    NSInteger rowIndex = indexPath.row;
    UIImage *background = nil;
    
    if (rowIndex == 0) {
        background = [UIImage imageNamed:@"cell_top.png"];
    } else if (rowIndex == rowCount - 1) {
        background = [UIImage imageNamed:@"cell_bottom.png"];
    } else {
        background = [UIImage imageNamed:@"cell_middle.png"];
    }
    
    return background;
}

- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender
{
    ViewListeDeroulanteSerie *viewListeDeroulanteSerie = segue.destinationViewController;
    NSIndexPath *indexPath = [self.tableView indexPathForSelectedRow];
    viewListeDeroulanteSerie.theme = [self.listeThemes objectAtIndex:indexPath.item];
    viewListeDeroulanteSerie.examenThematique = YES;
    
    
    NSLog(@"Le thème : ' %@ ' a été sélectionné " ,viewListeDeroulanteSerie.theme);
}

@end